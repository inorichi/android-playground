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

/**
 * View model of the character details screen.
 */
@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getCharacter: GetCharacter
) : ViewModel() {

  /**
   * The character id provided by navigation.
   */
  private val characterId = savedStateHandle.get<Int>(CHARACTER_KEY)!!

  /**
   * The state of the screen. Exposed externally as a read-only [StateFlow].
   */
  private val mutableState = MutableStateFlow(CharacterDetailsViewState.Empty)
  val state: StateFlow<CharacterDetailsViewState> get() = mutableState

  init {
    // Retrieve the character on launch
    getCharacter()
  }

  /**
   * Retrieves the current [characterId] and updates the state.
   */
  fun getCharacter() {
    viewModelScope.launch {
      mutableState.value = when (val result = getCharacter(characterId)) {
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

  companion object {
    const val CHARACTER_KEY = "characterId"
  }

}
