package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "recent_scans")
data class RecentScan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val query: String,
    val scanType: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSafe: Boolean,
    val threatProbability: Int,
    val explanation: String
)

@Entity(tableName = "community_reports")
data class CommunityReport(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val scammerDetail: String,
    val incidentType: String,
    val timestamp: Long = System.currentTimeMillis(),
    val description: String,
    val isAnonymous: Boolean,
    val isVerified: Boolean = false
)

@Dao
interface ScamShieldDao {
    @Query("SELECT * FROM recent_scans ORDER BY timestamp DESC")
    fun getAllRecentScans(): Flow<List<RecentScan>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(scan: RecentScan)

    @Query("SELECT * FROM community_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<CommunityReport>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: CommunityReport)

    @Query("DELETE FROM recent_scans")
    suspend fun clearAllScans()
}

@Database(entities = [RecentScan::class, CommunityReport::class], version = 1, exportSchema = false)
abstract class ScamShieldDatabase : RoomDatabase() {
    abstract fun dao(): ScamShieldDao

    companion object {
        @Volatile
        private var INSTANCE: ScamShieldDatabase? = null

        fun getDatabase(context: Context): ScamShieldDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScamShieldDatabase::class.java,
                    "scam_shield_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
