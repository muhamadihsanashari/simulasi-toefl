package com.fastwork.toefl.data.local.model

import java.io.Serializable

data class ModelSession(
    val session: String? = null,
    val dataTest: ModelFullTest? = null,
    val category: String? = null
) :
    Serializable {

    fun getConversionScore(): Int {
        var conversionListening = 24
        var conversionStructure = 20
        var conversionReading = 22
        when (dataTest?.listeningCorrect) {
            0 -> conversionListening = 24
            1 -> conversionListening = 25
            2 -> conversionListening = 26
            3 -> conversionListening = 27
            4 -> conversionListening = 28
            5 -> conversionListening = 29
            6 -> conversionListening = 30
            7 -> conversionListening = 31
            8 -> conversionListening = 32
            9 -> conversionListening = 32
            10 -> conversionListening = 33
            11 -> conversionListening = 35
            12 -> conversionListening = 37
            13 -> conversionListening = 37
            14 -> conversionListening = 38
            15 -> conversionListening = 41
            16 -> conversionListening = 41
            17 -> conversionListening = 42
            18 -> conversionListening = 43
            19 -> conversionListening = 44
            20 -> conversionListening = 45
            21 -> conversionListening = 45
            22 -> conversionListening = 46
            23 -> conversionListening = 47
            24 -> conversionListening = 47
            25 -> conversionListening = 48
            26 -> conversionListening = 48
            27 -> conversionListening = 49
            28 -> conversionListening = 49
            29 -> conversionListening = 50
            30 -> conversionListening = 51
            31 -> conversionListening = 51
            32 -> conversionListening = 52
            33 -> conversionListening = 52
            34 -> conversionListening = 53
            35 -> conversionListening = 54
            36 -> conversionListening = 54
            37 -> conversionListening = 55
            38 -> conversionListening = 56
            39 -> conversionListening = 57
            40 -> conversionListening = 57
            41 -> conversionListening = 58
            42 -> conversionListening = 59
            43 -> conversionListening = 60
            44 -> conversionListening = 61
            45 -> conversionListening = 62
            46 -> conversionListening = 63
            47 -> conversionListening = 65
            48 -> conversionListening = 66
            49 -> conversionListening = 67
            50 -> conversionListening = 68
        }
        when(dataTest?.structureCorrect){
            0 -> conversionStructure = 20
            1 -> conversionStructure = 20
            2 -> conversionStructure = 21
            3 -> conversionStructure = 22
            4 -> conversionStructure = 23
            5 -> conversionStructure = 25
            6 -> conversionStructure = 26
            7 -> conversionStructure = 27
            8 -> conversionStructure = 29
            9 -> conversionStructure = 31
            10 -> conversionStructure = 33
            11 -> conversionStructure = 35
            12 -> conversionStructure = 36
            13 -> conversionStructure = 37
            14 -> conversionStructure = 38
            15 -> conversionStructure = 40
            16 -> conversionStructure = 40
            17 -> conversionStructure = 41
            18 -> conversionStructure = 42
            19 -> conversionStructure = 43
            20 -> conversionStructure = 44
            21 -> conversionStructure = 45
            22 -> conversionStructure = 46
            23 -> conversionStructure = 47
            24 -> conversionStructure = 48
            25 -> conversionStructure = 49
            26 -> conversionStructure = 50
            27 -> conversionStructure = 51
            28 -> conversionStructure = 52
            29 -> conversionStructure = 53
            30 -> conversionStructure = 54
            31 -> conversionStructure = 55
            32 -> conversionStructure = 56
            33 -> conversionStructure = 57
            34 -> conversionStructure = 58
            35 -> conversionStructure = 60
            36 -> conversionStructure = 61
            37 -> conversionStructure = 63
            38 -> conversionStructure = 65
            39 -> conversionStructure = 67
            40 -> conversionStructure = 68
        }
        when (dataTest?.readingCorrect) {
            0 -> conversionReading = 22
            1 -> conversionReading = 21
            2 -> conversionReading = 22
            3 -> conversionReading = 23
            4 -> conversionReading = 23
            5 -> conversionReading = 24
            6 -> conversionReading = 25
            7 -> conversionReading = 26
            8 -> conversionReading = 28
            9 -> conversionReading = 28
            10 -> conversionReading = 29
            11 -> conversionReading = 30
            12 -> conversionReading = 31
            13 -> conversionReading = 32
            14 -> conversionReading = 34
            15 -> conversionReading = 35
            16 -> conversionReading = 36
            17 -> conversionReading = 37
            18 -> conversionReading = 38
            19 -> conversionReading = 39
            20 -> conversionReading = 40
            21 -> conversionReading = 41
            22 -> conversionReading = 42
            23 -> conversionReading = 43
            24 -> conversionReading = 43
            25 -> conversionReading = 44
            26 -> conversionReading = 45
            27 -> conversionReading = 46
            28 -> conversionReading = 46
            29 -> conversionReading = 47
            30 -> conversionReading = 48
            31 -> conversionReading = 48
            32 -> conversionReading = 49
            33 -> conversionReading = 50
            34 -> conversionReading = 51
            35 -> conversionReading = 52
            36 -> conversionReading = 52
            37 -> conversionReading = 53
            38 -> conversionReading = 54
            39 -> conversionReading = 54
            40 -> conversionReading = 55
            41 -> conversionReading = 56
            42 -> conversionReading = 57
            43 -> conversionReading = 58
            44 -> conversionReading = 59
            45 -> conversionReading = 60
            46 -> conversionReading = 61
            47 -> conversionReading = 63
            48 -> conversionReading = 65
            49 -> conversionReading = 66
            50 -> conversionReading = 67
        }
        return conversionListening + conversionStructure + conversionReading
    }
}
