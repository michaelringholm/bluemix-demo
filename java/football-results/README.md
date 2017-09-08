
1
down vote
accepted
Try two things: 1. Use the -p command to target your deployable artifact. This would look something like cf p APP_NAME -p PATH_TO_YOUR_WAR. I usually chain my build and deploy commands so: mvn clean package && cf p APP_NAME -p PATH_TO_YOUR_WAR.

If this doesn't work then you can specify a build pack. So cf p APP_NAME -p PATH_TO_YOUR_WAR -b SOME_BUILDPACK. You can see the available build packs by calling cf buildpacks.