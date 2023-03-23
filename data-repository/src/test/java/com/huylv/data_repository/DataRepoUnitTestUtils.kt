package com.huylv.data_repository

import com.huylv.domain.entity.Flavor

class DataRepoUnitTestUtils {

    fun generateFlavors(noOfSample: Int): List<Flavor> {
        val data = ArrayList<Flavor>()
        for (i in 1 until (noOfSample + 1)) {
            data.add(Flavor("Flavor $i", (i + 10).toFloat()))
        }
        return data
    }
}