package com.rinfinity.powerplay.utils

const val CONST_APP_BASE_URL = "https://www.powerplay.com"
const val CONST_APP_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"
const val CONST_APP_PROVIDER_AUTHORITY = "com.rinfinity.powerplay.provider"

interface Extensions {
    companion object {
        const val PNG = ".png"
    }
}

interface IntentParmas {
    companion object {
        const val SAVE_DRAWING_RESULT = "saveDrawingResult"
        const val PARAM_IMAGE_URI = "imageUri"
        const val PARAM_MARKER_COUNT = "imageMarkerCount"
        const val PARAM_IMAGE_NAME = "imageName"
        const val PARAM_IMAGE_ID = "imageId"
        const val PARAM_IMAGE_CREATION_TIME = "imageCreationTime"
        const val PARAM_POSITION = "position"
    }
}
