# Introduction #

Exercise 8: Given an image sequence, compute the motion image. Render the result as an image sequence.

# Theoretical Framework #

**Image difference**

> Given two images F(x,y) and G(x,y), the image difference H(x,y) = F(x,y) - G(x,y), for all pixels at (x,y) in F and G.


# Methodology #

In this exercise, a video is selected and the individual frames are extracted.
Image difference is performed on the frames to compute the motion image. The motion image is filtered using a 3x3 mean filter then thresholded at 15. The closing operator is applied on the binary image.


# Results #

Watch the resulting video in [YouTube](http://www.youtube.com/watch?v=sZrFU3ecC7Y), or

[download](http://cmsc291.googlecode.com/svn/trunk/data/motion.avi) the video.