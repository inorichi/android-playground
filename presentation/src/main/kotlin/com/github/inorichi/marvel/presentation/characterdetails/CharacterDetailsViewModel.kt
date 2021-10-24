package com.github.inorichi.marvel.presentation.characterdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.inorichi.marvel.domain.character.entity.CharacterDetails
import com.github.inorichi.marvel.domain.character.interactor.GetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    getCharacter.subscribe(characterId)
      .onEach { character = it }
      .launchIn(viewModelScope)
  }

}
