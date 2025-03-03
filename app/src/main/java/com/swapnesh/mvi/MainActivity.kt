package com.swapnesh.mvi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.swapnesh.mvi.api.AnimalService
import com.swapnesh.mvi.model.Animal
import com.swapnesh.mvi.ui.theme.MVITheme
import com.swapnesh.mvi.view.MainIntent
import com.swapnesh.mvi.view.MainState
import com.swapnesh.mvi.view.MainViewModel
import com.swapnesh.mvi.view.ViewModelFactory
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

   val mainViewModel: MainViewModel by lazy {
     ViewModelProvider(this, ViewModelFactory(AnimalService.api))
         .get(MainViewModel::class.java)
 }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onButtonClick :() -> Unit = {
                lifecycleScope.launch {
                    mainViewModel.userInent.send(MainIntent.FetchAnimals)
                }
        }

        setContent {
            MVITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(viewmodel = mainViewModel, onButtonClick = onButtonClick)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewmodel: MainViewModel, onButtonClick: () -> Unit){

   val state =  viewmodel.state.value

    when (state) {

        is MainState.Idel ->IdelScreen(onButtonClick)
        is MainState.Loading -> LoadingScreen()
        is MainState.Animals -> AnimalList(animal = state.animales)
        is MainState.Error ->{
            IdelScreen(onButtonClick)
            Toast.makeText(LocalContext.current,state.error, Toast.LENGTH_SHORT).show()
        }



    }

}

@Composable
fun IdelScreen(onButtonClick: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = onButtonClick) {
            Text(text = "Fetch Animals")
        }
    }
}

@Composable
fun LoadingScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun AnimalList(animal : List<Animal>){

    LazyColumn {
        items(items = animal){
            AnimalItem(animal = it)
            Divider(color = Color.LightGray, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
        }
    }



}

@Composable
fun AnimalItem(animal: Animal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val url = AnimalService.BASE_URL + animal.image
        val painter = rememberImagePainter(data = url)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.FillHeight
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp)) {
            Text(text = animal.name, fontWeight = FontWeight.Bold)
            Text(text = animal.location)
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MVITheme {
        MainScreen()
    }
}*/
