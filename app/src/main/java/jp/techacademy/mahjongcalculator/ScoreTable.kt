package jp.techacademy.mahjongcalculator

class ScoreTable {
    companion object {
        fun getBaseOtherPoint(fu: Int, han: Int, isKid: Boolean): Int {
            val basePoint = when (fu) {
                30 -> {
                    when (han) {
                        1 -> { if (!isKid) 1500 else 1000 }
                        2 -> { if (!isKid) 2900 else 2000 }
                        3 -> { if (!isKid) 5800 else 3900 }
                        4 -> { if (!isKid) 11600 else 7700 }
                        5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                40 -> {
                    when (han) {
                        1 -> { if (!isKid) 2000 else 1300 }
                        2 -> { if (!isKid) 3900 else 2600 }
                        3 -> { if (!isKid) 7700 else 5200 }
                        in 4..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                50 -> {
                    when (han) {
                        1 -> { if (!isKid) 2400 else 1600 }
                        2 -> { if (!isKid) 4800 else 3200 }
                        3 -> { if (!isKid) 9600 else 6400 }
                        in 4..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                60 -> {
                    when (han) {
                        1 -> { if (!isKid) 2900 else 2000 }
                        2 -> { if (!isKid) 5800 else 3900 }
                        3 -> { if (!isKid) 11600 else 7700 }
                        in 4..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                70 -> {
                    when (han) {
                        1 -> { if (!isKid) 3400 else 2300 }
                        2 -> { if (!isKid) 6800 else 4500 }
                        in 3..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                80 -> {
                    when (han) {
                        1 -> { if (!isKid) 3900 else 2600 }
                        2 -> { if (!isKid) 7700 else 5200 }
                        in 3..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                90 -> {
                    when (han) {
                        1 -> { if (!isKid) 4400 else 2900 }
                        2 -> { if (!isKid) 8700 else 5800 }
                        in 3..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                100 -> {
                    when (han) {
                        1 -> { if (!isKid) 4800 else 3200 }
                        2 -> { if (!isKid) 9600 else 6400 }
                        in 3..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                110 -> {
                    when (han) {
                        1 -> { if (!isKid) 5300 else 3600 }
                        2 -> { if (!isKid) 10600 else 7100 }
                        in 3..5 -> { manganPoint(isKid) }
                        in 6..7 -> { hanemanPoint(isKid) }
                        in 8..10 -> { baimanPoint(isKid) }
                        in 11..12 -> { sanbaimanPoint(isKid) }
                        else -> { yakumanPoint(isKid) }
                    }
                }
                else -> {
                    if (!isKid) 1 else 1
                }
            }
            return basePoint
        }

        private fun manganPoint(isKid: Boolean): Int {
            val basePoint: Int = if (!isKid) {
                12000
            } else {
                8000
            }
            return basePoint
        }

        private fun hanemanPoint(isKid: Boolean): Int {
            val basePoint: Int = if (!isKid) {
                18000
            } else {
                12000
            }
            return basePoint
        }

        private fun baimanPoint(isKid: Boolean): Int {
            val basePoint: Int = if (!isKid) {
                24000
            } else {
                18000
            }
            return basePoint
        }

        private fun sanbaimanPoint(isKid: Boolean): Int {
            val basePoint: Int = if (!isKid) {
                36000
            } else {
                24000
            }
            return basePoint
        }

        private fun yakumanPoint(isKid: Boolean): Int {
            val basePoint: Int = if (!isKid) {
                48000
            } else {
                32000
            }
            return basePoint
        }
    }
}