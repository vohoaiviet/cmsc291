### Introduction ###
In this exercise, our goal is to localize the plate number in a given car picture.

### Results and Discussion ###

The approach taken was to filter the original image(Figure 1) using first a 3x3 median filter and then a 3x3 guassian filter with standard deviation of 9. After which, the
edges were detected using laplacian filter with a threshold of 7.

The image containing only the edges was analyzed first vertically by counting the number of transitions from black to white and white to black. After this step, the vertical position of the plate number is obtained. Then the image is analyzed horizontally by computing the sum of intensity for each column of pixel within the vertical boundary obtained from the previous step (Figure 2). Figure 3 and 4 show the localized plate numbers.

**Figure 1. Original image**

|![http://cmsc291.googlecode.com/svn/trunk/data/exer3/test_022.jpg](http://cmsc291.googlecode.com/svn/trunk/data/exer3/test_022.jpg)|![http://cmsc291.googlecode.com/svn/trunk/data/exer3/lp0187.jpg](http://cmsc291.googlecode.com/svn/trunk/data/exer3/lp0187.jpg)|
|:----------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------|

**Figure 2. Scratch image containing results of vertical and horizontal analysis**

|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_scratch.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_scratch.jpg)|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_scratch2.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_scratch2.jpg)|
|:----------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------|

**Figure 3. Plate number localized**

|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_localization.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_localization.jpg)|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_localization2.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_localization2.jpg)|
|:--------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------|

**Figure 4. Plate number extracted from original image**

|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_number.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_number.jpg)|![http://cmsc291.googlecode.com/svn/trunk/tests/plate_number2.jpg](http://cmsc291.googlecode.com/svn/trunk/tests/plate_number2.jpg)|
|:--------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------|


### Conclusion ###

Based on the results obtained, it is evident that the approach implemented in this exercise to localize the plate number in a given car image does not provide a high percentage of success. Other methods will have to be investigated.

### References ###

1. http://javaanpr.sourceforge.net

### Source Code ###

http://cmsc291.googlecode.com/svn/trunk/src/main/com/jachsoft/cmsc291/exercises/PlateLocalization.java