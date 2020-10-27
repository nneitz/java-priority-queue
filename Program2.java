

import java.util.Random;

/*

Driver class

*/

public class Program2{

   public static void main(String [] args){
       PriorityQueue queue = new PriorityQueue();
       int num = 0;
       int maximumQueueLength = 0;
       
       for(int minutes = 0; minutes < 60; minutes++){
           //25% chance for a new customer to be added to the queue
           Random n = new Random();
           num = n.nextInt(4) + 1;
           
           if(num == 1)
           {
               queue.add(new PriorityCustomer());
               System.out.println("New customer added!  Queue length is now " + queue.getSize());       
               
               //Updates maximum queue size number if current size is larger than previous maximum
               if(maximumQueueLength < queue.getSize()){                                                  
                    maximumQueueLength = queue.getSize();
               }
           }

           PriorityCustomer cust = queue.getRoot();

           //Reduces servicetime if there is a customer in queue
           if(cust != null){
               cust.decServiceTime();
           
           //Remove from the queue if servicetime is over
               if(cust.getServiceTime() == 0){ 
                  queue.remove();
                  System.out.println("Customer serviced and removed from the queue.  Queue length is now " + queue.getSize());
               }
           }
           System.out.println("---------------------------------------------------");
       }
       
       //Displays total number of customers
       System.out.println("Total number of customers serviced: " + queue.getCustomerSum());
       //Displays longest line length
       System.out.println("Maximum line length during the simulation: " + maximumQueueLength);
   }
}


/*

PriorityQueue class

*/


class PriorityQueue {

   private PriorityCustomer[] heap;
   private int size;
   int customerSum = 0;
   
   public PriorityQueue () {
      heap = new PriorityCustomer[50];
      size = 0;
   }
   
   public PriorityQueue (int s) {
      heap = new PriorityCustomer [s];
      size = 0;
   }
   
   //getter for size of heap
   public int getSize () {
      return size;
   }
   
   //getter for root value of heap
   public PriorityCustomer getRoot(){
       return heap[1];
   }
   
   //getter for total amount of customers served
   public int getCustomerSum() {
      return customerSum;
   }
   
   public void add (PriorityCustomer c) {
      
     
     
      //checks if heap is full
      if (size + 2 > heap.length) {
         System.out.println ("The heap is full");
         return;
      }
      
      //increases the size
      size ++;
      
      //adds new object to heap
      heap [size] = c;
      
      //variable used to keep track of where the object is
      int index = size;
      
      //comparison loop used to sort new value in heap
      while (index > 1) {
      
         int parentIndex = index / 2;
      
         //comparison to determine if a swap with parent is needed
         if (heap[index].getPriority () > heap[parentIndex].getPriority()) {
            PriorityCustomer temporary = heap[index];
            heap[index] = heap[parentIndex];
            heap[parentIndex] = temporary;
            index = parentIndex;
         } else {
            //if no swap is needed, break out of the loop
            break;
         }
      }   
   }
   
   public PriorityCustomer remove () {
      
      //checks if heap is empty
      if (size == 0) {
         System.out.println ("The heap is already empty");
         return null;
      }
      
      //increase marker holding variable representing number of customers served
      customerSum++;
    
      PriorityCustomer temporary = heap [1];
      
      //move last object to the root
      heap [1] = heap [size];
      heap [size] = null;
      size--;
      int index = 1;
      
      //loop used to determine if root needs to be replaced
      while (index <= size / 2) {
      
         //this stores index and values of children
         int leftChildIndex = index * 2;
         int rightChildIndex = leftChildIndex + 1;
         
         int leftChildValue = heap [leftChildIndex].getPriority ();
         int rightChildValue = Integer.MIN_VALUE;
         
         //checks for right child
         if (rightChildIndex <= size) {
            rightChildValue = heap [rightChildIndex].getPriority ();
         }
         
         //checks for the largest of two values in heap, swaps them if necessary
         int largerValue;
         int largerIndex;
         
         if (rightChildValue > leftChildValue) {
            largerValue = rightChildValue;
            largerIndex = rightChildIndex;
         } else {
            largerValue = leftChildValue;
            largerIndex = leftChildIndex;
         }
         
         if (heap[index].getPriority () < largerValue) {
            PriorityCustomer swap = heap [index];
            heap [index] = heap [largerIndex];
            heap [largerIndex] = swap;
            index = largerIndex;
         } else {
            //if no swap is needed, break out of the loop
            break;
         }
         
      }
      
      //returns original root value
      return temporary;
   
      
   }


}