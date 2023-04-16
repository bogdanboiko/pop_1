with ada.Text_IO; use Ada.Text_IO;
with Ada.Numerics.Discrete_Random;
with Ada.Numerics.Generic_Elementary_Functions;

procedure Main is
   thread_num : constant Integer := 8;
   arr_length: Integer := 106000;
   arr: array(1..arr_length) of Integer;

   function random_number_in_range(minimum, maximum : Integer) return Integer is
      type number_range is new Integer range minimum..maximum;
      package Rand_Int is new ada.numerics.Discrete_Random(number_range);
      use Rand_Int;
      gen: Generator;
      num: number_range;
   begin
      reset(gen);
      num := random(gen);
      return Integer(num);
   end random_number_in_range;

   procedure Init_Arr is
   begin
      for i in 1..arr_length loop
         arr(i) := random_number_in_range(1, 500000);
      end loop;

      arr(random_number_in_range(1, arr_length)) := -1;
   end Init_Arr;

   function find_min_index(start_index, finish_index : in Integer) return Integer is
      min_index : Integer := start_index;
      min_value : Integer := arr(min_index);
   begin
      for i in start_index..finish_index loop
         if (arr(i) < min_value) then
            min_index := i;
            min_value := arr(min_index);
         end if;

      end loop;
      return min_index;
   end find_min_index;

   protected part_manager is
      procedure set_part_min(min_index : in Integer);
      entry get_min(min_index : out Integer);
   private
      tasks_count : Integer := 0;
      result_min_index : Integer := 1;
      result_min : Integer := arr(1);
   end part_manager;

   protected body part_manager is
      procedure set_part_min(min_index : in Integer) is
      begin
         if (arr(min_index) < result_min) then
            result_min_index := min_index;
            result_min := arr(min_index);
         end if;

         tasks_count := tasks_count + 1;
      end set_part_min;

      entry get_min(min_index : out Integer) when tasks_count = thread_num is
      begin
         min_index := result_min_index;
      end get_min;

   end part_manager;

   task type find_min_element_index_task is
      entry start(start_index, finish_index : in Integer);
   end find_min_element_index_task;

   task body find_min_element_index_task is
      start_index, finish_index : Integer;
   begin
      accept start (start_index, finish_index : in Integer) do
         find_min_element_index_task.start_index := start_index;
         find_min_element_index_task.finish_index := finish_index;
      end start;
      part_manager.set_part_min(find_min_index(start_index => start_index, finish_index => finish_index));
   end find_min_element_index_task;

   function parse_arr return Integer is
      result_min_index : Integer;
      step: Integer := (arr_length + (thread_num - 1)) / thread_num;
      start_index: Integer;
      end_index: Integer;
      threads : array(1..thread_num) of find_min_element_index_task;
      thread_counter : Integer := 1;
      loop_counter : Integer := 1;
   begin
      while loop_counter < arr'Length loop
         start_index := loop_counter;
         if ((loop_counter + step) < arr'Length) then
            end_index := loop_counter + step;
         else
            end_index := arr'Length;
         end if;
         threads(thread_counter).start(start_index, end_index);
         thread_counter := thread_counter + 1;
         loop_counter := loop_counter + step;
      end loop;
      part_manager.get_min(result_min_index);
      return result_min_index;
   end parse_arr;
      result_index : Integer;
   begin
      Init_Arr;
      result_index := parse_arr;
      Put_Line("Index: " & result_index'img & " Value: " & arr(result_index)'img);
   end Main;


