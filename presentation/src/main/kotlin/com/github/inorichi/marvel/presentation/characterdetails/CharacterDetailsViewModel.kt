package com.github.inorichi.marvel.presentation.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getCharacter: GetCharacter
) : ViewModel() {

  private val characterId = savedStateHandle.get<Int>("characterId")!!

  private val mutableState = MutableStateFlow(CharacterDetailsViewState.Empty)
  val state: StateFlow<CharacterDetailsViewState> get() = mutableState

  init {
    getCharacter()
  }

  fun getCharacter() {
    viewModelScope.launch {
      mutableState.value = when (val result = getCharacter.await(characterId)) {
        is GetCharacter.Result.Success -> {
           CharacterDetailsViewState(result.character, false, null)
        }
        is GetCharacter.Result.Error -> {
          CharacterDetailsViewState(null, false, result.error)
        }
        GetCharacter.Result.NotFound -> {
          CharacterDetailsViewState(null, false, null)
        }
      }
    }
  }

}
