package com.pawporation.petpilot.utils

import com.pawporation.petpilot.models.PawDataModel

interface Communicator {

    fun passPawData(pawDataInfo: PawDataModel)

}