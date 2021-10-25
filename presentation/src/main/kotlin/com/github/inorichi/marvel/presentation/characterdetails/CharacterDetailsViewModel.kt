package com.github.inorichi.marvel.presentation.characterdetails

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  getCharacter: GetCharacter
) : ViewModel() {

  val characterId = savedStateHandle.get<Int>("characterId")!!

  var character by mutableStateOf<CharacterDetails?>(null)
    private set

  init {
    viewModelScope.launch {
      val result = getCharacter.await(characterId)
      if (result is GetCharacter.Result.Success) {
        character = result.character
      } else if (result is GetCharacter.Result.Error) {
        Log.w("Err", result.error.message.orEmpty())
      }
    }
  }

}
