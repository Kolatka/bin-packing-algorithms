# What is a bin packing problem
It's about placing goods of various sizes into the smallest number of the containers. Good solution could have many benefits, such as saving space while storing items, reducing the number of trucks in transit, improving production etc.

With a small number of elements it's easy to find the optimal solution. However, with their growth it becomes almost impossible. This is due to the fact that by placing subsequent elements in the containers we can't know if we are doing this in the best way. This makes it an NP-difficult problem. However, there are many approximation algorithms, e.g. First Fit, Next Fit, Best Fit. With more elements, they will not find an ideal solution, but very close to optimal.

# Implemented algorithms

* Next Fit
* First Fit
* Brute Force
* Branch and Bound

Every algorithm has 3 versions: elements sorted descending, elements sorted ascending and random arrangement

Branch and bound method consists in dividing the set of acceptable solutions into subsets. The subset is equivalent to the input problem but smaller in size (sub-problem).
![Brute Force vs Branch and Bound](https://i.imgur.com/YDa7uf7.png)

# How to use

Main window has 4 panels. On the left top you can generate set of elements. To the right of this items are displayed. The lower left panel contains algorithm selection options. Result of this process is displayed in bottom right text area.

![Main Window](https://i.imgur.com/KsuRPe4.png)


# How to run
To run this app you need only Java 8 and JavaFX (for displaying gui)

# To Do
* Implementation of Best Fit algorithm
* Visualization of packing items into containers
* Move it into web
