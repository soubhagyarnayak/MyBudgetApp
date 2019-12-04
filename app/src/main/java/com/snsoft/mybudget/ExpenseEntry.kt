package com.snsoft.mybudget

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import java.io.Serializable
import java.util.*

@Entity(tableName = "expenseEntity")
data class ExpenseEntry(@PrimaryKey() var id: UUID,
                        @ColumnInfo(name = "description") var description: String,
                        @ColumnInfo(name = "amount") var amount: Double,
                        @ColumnInfo(name = "category") var category: Int,
                        @ColumnInfo(name= "createTime", index = true) var createTime: Date,
                        @ColumnInfo(name= "updateTime") var updateTime: Date
): Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(UUID::class.java.classLoader) as UUID,
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            Date(parcel.readLong()),
            Date(parcel.readLong())) {
    }

    constructor(): this(UUID.randomUUID(),"",0.0,0,Calendar.getInstance().time,Calendar.getInstance().time)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(description)
        parcel.writeDouble(amount)
        parcel.writeInt(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseEntry> {
        override fun createFromParcel(parcel: Parcel): ExpenseEntry {
            return ExpenseEntry(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseEntry?> {
            return arrayOfNulls(size)
        }
    }
}