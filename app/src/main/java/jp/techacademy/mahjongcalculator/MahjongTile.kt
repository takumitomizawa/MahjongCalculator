package jp.techacademy.mahjongcalculator

import android.os.Parcel
import android.os.Parcelable

data class MahjongTile(
    val number: Int,
    val tileType: String,
    val isBack: Boolean,
    val isRevealed: Boolean,
    val imageResourceId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(tileType)
        parcel.writeByte(if (isBack) 1 else 0)
        parcel.writeByte(if (isRevealed) 1 else 0)
        parcel.writeInt(imageResourceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MahjongTile> {
        override fun createFromParcel(parcel: Parcel): MahjongTile {
            return MahjongTile(parcel)
        }

        override fun newArray(size: Int): Array<MahjongTile?> {
            return arrayOfNulls(size)
        }
    }
}