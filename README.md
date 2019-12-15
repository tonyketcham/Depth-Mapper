<h1>Depth Mapper</h1>

Generates a depth map from a focal stack of images, using ideas proposed in [this Stanford graphics paper](https://graphics.stanford.edu/papers/focalstack/focalstack.pdf). Supports JPG input stacks, with the goal of supporting a DNG in-and-out pipeline. A focal stack of DNG images would be fed in as input with one single DNG exported, containing an embedded depth map. This would allow DSLR users the ability to take advantage of the depth masking capabilities of Adobe Camera Raw, Lightroom, and other RAW processors. Previously, only stereoscopic cameras, light field cameras, and digital renders could make use of these masking features.

This project strongly utilizes OpenCV and eventually OpenCL through [JavaCV](https://github.com/bytedeco/javacv). This wrapper, unlike native OpenCV for Java, allows use of Transparent API for seamless connection between CV and CL.

<h2> Installation </h2>

A Maven Eclipse project is provided. Make sure you have OpenCV 4.1.1 installed as a User Library in eclipse as 'opencv'. The project should function with OpenCV 3.* as well, however this has not been tested.
