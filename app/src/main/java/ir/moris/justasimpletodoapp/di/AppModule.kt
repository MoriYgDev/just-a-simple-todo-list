package ir.moris.justasimpletodoapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.moris.justasimpletodoapp.feature_note.data.data_scource.NoteDatabase
import ir.moris.justasimpletodoapp.feature_note.data.data_scource.NoteDatabase.Companion.DATABASE_NAME
import ir.moris.justasimpletodoapp.feature_note.data.repository.NoteRepositoryImpl
import ir.moris.justasimpletodoapp.feature_note.domain.repository.NoteRepository
import ir.moris.justasimpletodoapp.feature_note.domain.use_case.AddNote
import ir.moris.justasimpletodoapp.feature_note.domain.use_case.DeleteNote
import ir.moris.justasimpletodoapp.feature_note.domain.use_case.GetNotes
import ir.moris.justasimpletodoapp.feature_note.domain.use_case.NoteUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(app, NoteDatabase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository)
        )
    }
}