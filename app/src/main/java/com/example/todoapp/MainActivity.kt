package com.example.todoapp

import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.TodoAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApp()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoAPPTheme {
        Greeting("Android")
    }
}


@Composable
fun ButtonClick(modifier: Modifier = Modifier) {
//    var number:Int=0
    var number by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("You've Clicked the button this many time")
        Text("$number", fontSize = 50.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { number++; Log.i("number", "$number") }) { Text("Click Me!") }
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonClickPreviewe() {
    ButtonClick()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(modifier: Modifier = Modifier) {
    var tasks by remember {
        mutableStateOf(
            listOf(
                Task(id = 1, name = "Belajar Jetpack Compose", isCompleted = true),
                Task(id = 2, name = "Tidur", isCompleted = false)
            )
        )
    }
    var taskName by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(Color(0xff6854a4)),
            title = {
                Text(
                    "TODO APP",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(8.dp)),
                value = taskName, onValueChange = { taskName = it })

            Button(
                onClick = {
                    val newTask = Task(id = tasks.size + 1, name = taskName, isCompleted = false)
                    // Menambahkan ke list
                    tasks = tasks + newTask
                    // Mengosongkan TextField
                    taskName = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                items(tasks.size) { task ->
                    TaskItem(task = tasks.get(task)) { isCompleted ->
                        // Update tasks list here
                        tasks =
                            tasks.map { it.copy(isCompleted = if (it.id == tasks.get(task).id) isCompleted else it.isCompleted) }
                    } // Update with logic to update task completion
                }
            }


        }
    }

}

@Preview(showBackground = true)
@Composable
private fun TodoAppPreview() {
    TodoApp()
}

@Composable
fun TaskItem(task: Task, onUpdateTask: (Boolean) -> Unit) { // Update parameter
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { isChecked ->
                onUpdateTask(isChecked)  // Pass updated value to callback
            }
        )
        Text(task.name, fontSize = 20.sp)
    }
}