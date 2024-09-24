package com.gzaber.remindme.di

import com.gzaber.remindme.ui.addedit.AddEditViewModel
import com.gzaber.remindme.ui.reminders.RemindersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RemindersViewModel(get()) }
    viewModel { AddEditViewModel(get(), get()) }
}