with Ada.Text_IO;

procedure Main is
   task type break_thread is
      entry Init(trigger_id : in Integer; dur_time : in Standard.Duration);
   end break_thread;

   task type main_thread is
      entry Timer_trigger(id : in Integer; step : in Long_Long_Integer);
   end main_thread;


   triggers : Array(1..5) Of Boolean := (others => False);
   pragma Atomic(triggers);

   task body break_thread is
      duration : Standard.Duration;
      trigger_id : Integer;
   begin
      accept Init(trigger_id : in Integer; dur_time : in Standard.Duration) do
         break_thread.trigger_id := trigger_id;
         break_thread.duration := dur_time;
      end Init;
      delay duration;
      triggers(trigger_id) := True;
   end break_thread;

   task body main_thread is
      id : Integer;
      sum : Long_Long_Integer := 0;
      step : Long_Long_Integer;
   begin
      accept Timer_trigger(id : in Integer; step : in Long_Long_Integer) do
         main_thread.id := id;
         main_thread.step := step;
      end Timer_trigger;

      loop
         sum := sum + step;
         exit when triggers(id);
      end loop;
      delay 1.0;

      Ada.Text_IO.Put_Line(id'Img & " - " & sum'Img);
   end main_thread;

   thread_quantity : Integer := 5;

   durations : Array(1..thread_quantity) Of Duration := (5.0, 1.0, 3.0, 5.0, 0.5);
   steps : Array(1..thread_quantity) Of Long_Long_Integer := (1, 1, 1, 1, 1);
   tasks : Array(1..thread_quantity) Of main_thread;
   breakers : Array(1..thread_quantity) Of break_thread;
   begin
      for I in triggers'Range Loop
         tasks(I).Timer_trigger(I, steps(I));
         breakers(I).Init(I, durations(I));
      end Loop;
      null;
   end Main;
