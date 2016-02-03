### Introduction ###

One application of image processing is image retrieval. Given an
input image, return a set of the most similar images from a given
collection. One challenge in image retrieval is defining similarity
among images. However, before similarity can be defined, there must
be a way to specify the features that can be used for comparison.
One way to describe an image is by its color composition, specifically
color histogram. In this exercise, a prototype image retrieval system is
implemented using color histogram as basis for similarity comparison.
Euclidean distance is used to compute the similarity value.

### Results and Discussion ###

The implemented system, COBAIR, is a web application that allows the
user to specify an image URL as input. The system will return the most
similar images to the input image from the collection after the user clicks the Search button. The system also allows the user to add the target image to the collection
by clicking the Add button. When adding an image to the collection,
the system does not store the image itself but only the location (URL) and the computed normalized image descriptor, in this case color histogram. The architecture of the system, however,
makes it easy to add other types of image content descriptor such as texture and shape.

The prototype system is not optimized. The feature vector has a dimension
of 768 since the RGB colorspace was used. Dimension reduction techniques like PCA can
be applied. Also, the retrieval itself is slow because the entire image collection is searched sequentially and no attempt was made to use a DBMS, clustering, or hashing. The prototype system was developed using Java Server Pages for the front end.

http://cmsc291.googlecode.com/svn/trunk/src/main/com/jachsoft/cbir/

http://cmsc291.googlecode.com/svn/cobair/


![http://cmsc291.googlecode.com/svn/cobair/cobair.png](http://cmsc291.googlecode.com/svn/cobair/cobair.png)


### References ###

1. http://old.hki.uni-koeln.de/teach/ws0708/VIP/tag9/ch01_Long_v40-proof.pdf