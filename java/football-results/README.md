# Sample Java Spring MVC project for the IBM BlueMix platform using Liberty

# Project Structure
Ensure a valid maven WAR structure, such as placing web resources in /src/main/webapp
Ensure a valid pom.xml file
Ensure a valid manifest.yml file for the BlueMix platform

# Manifest.yml
Ensure that sufficient memory is allocated (512M was needed in for this small project, ouch!)
Ensure that you point to the generated WAR file for web archives
Specify the build pack for faster deployment (avoid auto detection)

# @Stelinno - Michael Sundgaard