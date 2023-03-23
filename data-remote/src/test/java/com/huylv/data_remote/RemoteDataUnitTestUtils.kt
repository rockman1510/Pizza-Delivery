package com.huylv.data_remote

import com.huylv.data_remote.network.flavor.model.FlavorApiModel
import com.huylv.domain.entity.Flavor

class RemoteDataUnitTestUtils {

    fun generateFlavorApiModels(noOfSample: Int): List<FlavorApiModel> {
        val data = ArrayList<FlavorApiModel>()
        for (i in 1 until (noOfSample + 1)) {
            data.add(FlavorApiModel("Flavor $i", (i + 10).toFloat()))
        }
        return data
    }

    fun convertToFlavors(inputData: List<FlavorApiModel>?): List<Flavor> {
        val data = ArrayList<Flavor>()
        inputData?.forEach {
            data.add(Flavor(it.name, it.price))
        }
        return data
    }

}