package com.yellowfeet.core.util;

import java.util.List;

final class ArrayUtilExtra {
	private ArrayUtilExtra() {};
	
	/*
	 * QuickSort extras
	 */
	static <T> void qsort(T[] arr, int lo, int hi, Sortable<T> s) {
		if(lo < hi) {
			qsortPartition(arr, lo, hi, s);
		}
	}
	
	static <T> void qsortPartition(T[] arr, int lo, int hi, Sortable<T> s) {
		float pivot = s.getValue(arr[(lo + hi) / 2]);
		
		if(hi - lo < 2) return;
		
		int i = lo;
		int j = hi;
		
		while(true) {
			while(s.getValue(arr[i]) < pivot) i++;
			while(s.getValue(arr[j]) > pivot) j--;
			
			if(i >= j) {
				qsortPartition(arr, lo, j, s);
				qsortPartition(arr, lo, i, s);
				return;
			}
			
			T temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}
	
	
	static <T> void qsort(List<T> arr, int lo, int hi, Sortable<T> s) {
		if(lo < hi) {
			qsortPartition(arr, lo, hi, s);
		}
	}
	
	private static <T> void qsortPartition(List<T> arr, int lo, int hi, Sortable<T> s) {
		float pivot = s.getValue(arr.get((lo + hi) / 2));
		
		if(hi - lo < 2) return;
		
		int i = lo;
		int j = hi;
		
		while(true) {
			while(s.getValue(arr.get(i)) < pivot) i++;
			while(s.getValue(arr.get(j)) > pivot) j--;
			
			if(i >= j) {
				qsortPartition(arr, lo, j, s);
				qsortPartition(arr, lo, i, s);
				return;
			}
			
			T temp = arr.get(i);
			arr.set(i, arr.get(j));
			arr.set(j, temp);
		}
	}
	
}
