package com.github.pploszczyca.expensetrackerv2.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
internal data class WalletEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val type: WalletType = WalletType.CASH,
)
