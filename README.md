<h1>Depth Mapper</h1>

[WIP] Generates a depth map from a focal stack of images.<sup>[1]</sup> Supports Jpeg, soon to support DNG.

Makes use of OpenCV and an enhanced Java implementation of ExifTool.<sup>[2]</sup>

<h2> Installation </h2>

A Maven Eclipse project is provided. Make sure you have OpenCV 4.1.1 installed as a User Library in eclipse as 'opencv'. The project should function with OpenCV 3.* as well, however this has not been tested.

<h2> References </h2>
<sup>[1]</sup> [Focal Stack Compositing for Depth of Field Control](https://graphics.stanford.edu/papers/focalstack/focalstack.pdf)
<sup>[2]</sup> [Java ExifTool](https://github.com/rkalla/exiftool)
