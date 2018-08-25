package com.example.miles.simplereminders

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.miles.simplereminders.reminders.Reminder
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderDaoTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var reminderDao: ReminderDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        try {
            database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        }
        catch (e: Exception) {
            Log.i("test", e.message)
        }
        reminderDao = database.reminderDao()
    }

    @Test
    fun testAddingAndRetrievingData() {
        // 1
        val preInsertRetrievedCategories = reminderDao.getAll()

        //2
        val listCategory = Reminder("Feed Lollie", 1)
        reminderDao.insertAll(listCategory)

        //3
        val postInsertRetrievedCategories = reminderDao.getAll()
        val sizeDifference = postInsertRetrievedCategories.size - preInsertRetrievedCategories.size
        Assert.assertEquals(1, sizeDifference)
        val retrievedCategory = postInsertRetrievedCategories.last()
        Assert.assertEquals("Feed Lollie", retrievedCategory.reminderString)
    }

    @After
    fun tearDown() {
        database.close()
    }
}