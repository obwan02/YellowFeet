package com.yellowfeet.core.util;

import java.io.PrintStream;
import java.util.List;

public class ArrayUtil {

	public static <T> void QuickSort(T[] arr, Sortable<T> s) {
		ArrayUtilExtra.qsort(arr, 0, arr.length - 1, s);
	}
	
	public static <T> void InsertionSort(T[] arr, Sortable<T> s) {
		for(int i = 1; i < arr.length; i++) {
			float max = s.getValue(arr[i - 1]);
			if(s.getValue(arr[i]) < max) {
				T swap = arr[i];
				for(int j = 0; j < i; j++) {
					if(s.getValue(swap) < s.getValue(arr[j])) {
						T temp = arr[j];
						arr[j] = swap;
						swap = temp;
					}
				}
				
				arr[i] = swap;
			}
		}
	}
	
	public static <T> void printArray(T[] arr, PrintStream out) {
		for(T obj : arr) {
			out.print(obj + ", ");
		}
		
		out.println();
	}
	
	//List methods
	public static <T> void QuickSort(List<T> arr, Sortable<T> s) {
		ArrayUtilExtra.qsort(arr, 0, arr.size() - 1, s);
	}
	
	public static <T> void InsertionSort(List<T> arr, Sortable<T> s) {
		for(int i = 1; i < arr.size(); i++) {
			float max = s.getValue(arr.get(i - 1));
			if(s.getValue(arr.get(i)) < max) {
				T swap = arr.get(i);
				for(int j = 0; j < i; j++) {
					if(s.getValue(swap) < s.getValue(arr.get(j))) {
						T temp = arr.get(j);
						arr.set(j, swap);
						swap = temp;
					}
				}
				
				arr.set(i, swap);
			}
		}
	}
	
	public static <T> void printArray(List<T> arr, PrintStream out) {
		for(T obj : arr) {
			out.print(obj + ", ");
		}
		
		out.println();
	}
	
}
