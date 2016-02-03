# Introduction #

In this exercise, our goal is to extract the results of the SET form. The SET form is a jpg file of the scanned paper-based SET questionnaire. We are also given a file which contains the coordinates of the options in the form.


# Details #

The form image was binarized so that only two colors are used by the
image. Then a bounding box was created to contain the options. The initial
bounding box created was adjusted to obtain the minimum that will contain the circle.
A frequency count of black pixels within the bounding box was obtained. Sorting based
on the frequency count was done. The one with the highest frequency count
was considered the choice of the respondent.

To detect crossed out options, the perimeter of the bounding boxes were compared.


Source image:
![http://cmsc291.googlecode.com/svn/trunk/data/exer1/0035.jpg](http://cmsc291.googlecode.com/svn/trunk/data/exer1/0035.jpg)
Result image:
![http://cmsc291.googlecode.com/svn/trunk/data/exer1/0035-result.jpg](http://cmsc291.googlecode.com/svn/trunk/data/exer1/0035-result.jpg)


Souce code:
http://cmsc291.googlecode.com/svn/trunk/src/main/com/jachsoft/cmsc291/exer1/Exercise1.java