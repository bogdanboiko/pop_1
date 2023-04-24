with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;

with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is
   package String_Lists is new Indefinite_Doubly_Linked_Lists (String);
   use String_Lists;
   Storage_Size: Integer := 5;
   Item_Numbers: Integer := 11;
   Storage : List;

   Access_Storage : Counting_Semaphore (1, Default_Ceiling);
   Full_Storage   : Counting_Semaphore (Storage_Size, Default_Ceiling);
   Empty_Storage  : Counting_Semaphore (0, Default_Ceiling);

   task type Producer is
      entry Start(Item_Numbers : in Integer);
   end Producer;

   task body Producer is
      items : Integer;
   begin
      accept Start (Item_Numbers : in Integer) do
         Producer.items := Item_Numbers;
      end Start;

      for i in 1 .. items loop
         Full_Storage.Seize;
         Access_Storage.Seize;

         Storage.Append ("item " & i'Img);
         Put_Line ("Added item " & i'Img);

         Access_Storage.Release;
         Empty_Storage.Release;
      end loop;
   end Producer;

   task type Consumer is
      entry Start(Item_Numbers : in Integer);
   end Consumer;

   task body Consumer is
      items : Integer;
   begin
      accept Start (Item_Numbers : in Integer) do
         Consumer.items := Item_Numbers;
      end Start;

      for i in 1 .. items loop
         Put_Line ("Waiting " & i'Img);
         Empty_Storage.Seize;
         Access_Storage.Seize;

         declare
            item : String := First_Element (Storage);
         begin
            Put_Line ("Took " & item);
         end;

         Storage.Delete_First;
         Access_Storage.Release;
         Full_Storage.Release;
         Put_Line ("Ended " & i'Img);
      end loop;
   end Consumer;


   producer_quantity: Integer := 2;
   consumer_quantity: Integer := 9;
   consumers : array(1.. consumer_quantity) of Consumer;
   producers : array(1.. producer_quantity) of Producer;
   producer_step : Integer := Item_Numbers / producer_quantity;
   consumer_step : Integer := Item_Numbers / consumer_quantity;
   producer_remainder : Integer := Item_Numbers mod producer_quantity;
   consumer_reminder : Integer := Item_Numbers mod consumer_quantity;
begin
   for i in 1.. producer_quantity loop
      if (i = producer_quantity and producer_remainder > 0) then
         producers(i).Start(Item_Numbers - (i - 1) * producer_step);
      else
         producers(i).Start(producer_step);
      end if;
   end loop;
   for i in 1.. consumer_quantity loop
      if (i = consumer_quantity and consumer_reminder > 0) then
         consumers(i).Start(Item_Numbers - (i - 1) * consumer_step);
      else
         consumers(i).Start(consumer_step);
      end if;
   end loop;
end Main;
