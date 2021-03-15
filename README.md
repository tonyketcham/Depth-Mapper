# 📷 Depth Mapper

Generates a depth map from a focal stack of images, using ideas proposed in this Stanford graphics paper: https://graphics.stanford.edu/papers/focalstack/focalstack.pdf.

Supports JPG input stacks, with the goal of supporting a DNG in-and-out pipeline. A focal stack of DNG images would be fed in as input with one single DNG exported, containing an embedded depth map. This would allow DSLR users the ability to take advantage of the depth masking capabilities of Adobe Camera Raw, Lightroom, and other RAW processors. Previously, only stereoscopic cameras, light field cameras, and digital renders could make use of these masking features.


This project strongly utilizes OpenCV and eventually OpenCL through [JavaCV](https://github.com/bytedeco/javacv). This wrapper, unlike native OpenCV for Java, allows use of Transparent API for seamless connection between CV and CL.

## 🗜️ Example
Take this highly textured scene of 32 input images:

### Image in the Stack

![Image with shallow depth of field](/src/TestImages/_0009_Highly%20Textured%20Input%20-%2009.jpg)

### 📚 Focus Stack

![Focus stack of highly textured scene](/src/gitresources/Focus%20Stacked.jpg)

### 📦 Depth Map

![Depth map of highly textured scene](/src/gitresources/Depth%20Map.jpg)

## Current State + Future Improvements
The algorithm for performing depth mapping considers the highest rate of change for a given pixel against every pixel at the same coordinate in the remaining images. As it is, the depth mapping performs best assuming images are aligned prior to input. The current alignment algorithm has a poor runtime for high megapixel image stacks (although it does provide quite an accurate alignment) but this time complexity can be improved greatly through a careful combination of ECC and homography-based image alignment. 

#### 🔧 Improvements

- DNG file handling will be implemented for use of the generated depth map as an embedded map for the raw focal stack it represents.
- The depth mapping algorithm will be significantly improved with image segmentation to constrain regions of pixels to a certain set of input files, as to reduce the noise of non-discriminable regions (regions with very little texture). This reduces haloing around sharp edges.
- Additional use of OpenCL to improve execution time of image alignment.


## 🔬 Installation

A Maven Eclipse project is provided. Make sure you have `OpenCV 4.1.1` installed as a User Library in eclipse as `opencv`. The project should function with `OpenCV 3.*` as well, however this has not been tested.

The `Launcher.java` should be ran and the included folder of test images will be referenced to generate a depth map. Feel free to include your own image stack and play around with it!
