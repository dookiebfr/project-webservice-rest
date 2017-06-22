IMAGE RESIZE SCENARIO

This spring boot application receives a REST request to resize an image to a different size.
The request includes the URL param as the following example:

Sample host:
http://localhost:8082/image-resize-1.0-SNAPSHOT/resizeImage

Sample param:
url=https://s-media-cache-ak0.pinimg.com/originals/80/6e/38/806e38b2da4329b93b1c0307da9dbfe4.jpg

Sample request:
http://localhost:8082/image-resize-1.0-SNAPSHOT/resizeImage?url=https://s-media-cache-ak0.pinimg.com/originals/80/6e/38/806e38b2da4329b93b1c0307da9dbfe4.jpg

The source code can be found on:
https://github.com/dookiebfr/project-webservice-rest

Build and deploy the .war file to a tomcat server (or equivalent) to use the application.

Original and optimized images will be stored on file directory:
./images/original/
./images/thumbnail/