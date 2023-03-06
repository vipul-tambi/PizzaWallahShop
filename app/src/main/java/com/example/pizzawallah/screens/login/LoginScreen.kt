package com.example.pizzawallah.screens.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzawallah.R
import com.example.pizzawallah.navigation.PizzaScreens


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController,
            viewModel: LoginScreenViewModel= androidx.lifecycle.viewmodel.compose.viewModel()){
    val showLoginForm= rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = Modifier.fillMaxSize(),
        color=Color.Green.copy(0.2f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {
            Scaffold(topBar = {
                TopAppBar(modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth(),
                    backgroundColor =Color.Green,
                elevation=5.dp) {
                    Text(text = "Pizza Wallah")
                }
            },
                modifier = Modifier.height(40.dp)
            ) {}
            Spacer(modifier = Modifier.height(50.dp))
            
            if(showLoginForm.value) {
                UserForm(loading = false, isCreateAccount = false) { username, email, password ->
                    viewModel.signInWithNameEmailPassword(email,password){
                        navController.navigate(PizzaScreens.PizzaHomeScreen.name)
                    }

                }
            }
            else
            {
                UserForm(loading = false, isCreateAccount = true){ username, email, password ->
                    viewModel.createUserWithNameEmailPassword(email,password,username){
                        navController.navigate(PizzaScreens.PizzaHomeScreen.name)
                    }

                }
                
            }


            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                val text = if (showLoginForm.value) "Sign up" else "Login"
                Text(text = "New User?")
                Text(text,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value

                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondaryVariant)

            }
        }

        
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading:Boolean=false,
    isCreateAccount:Boolean=false,
    onDone:(String,String,String)->Unit={username,email,pwd->

    }
){
    val username= rememberSaveable {
        mutableStateOf("")
    }
    val email=rememberSaveable{
        mutableStateOf("")
    }
    val password=rememberSaveable{
        mutableStateOf("")
    }
    val passwordVisibility=rememberSaveable{
        mutableStateOf(false)
    }

    val passwordFocusRequest=FocusRequester.Default

    val keyboardController=LocalSoftwareKeyboardController.current

    val valid= if(isCreateAccount) {
        remember(email.value, password.value) {
            email.value.trim().isNotEmpty() && password.value.trim()
                .isNotEmpty() && username.value.trim().isNotEmpty()
        }
    }
    else
    {
        remember(email.value, password.value) {
            email.value.trim().isNotEmpty() && password.value.trim()
                .isNotEmpty()
        }
    }


    val modifier= Modifier
        .height(300.dp)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally){
        if(isCreateAccount)
            Text(text = stringResource(id = R.string.create_acct), modifier = Modifier.padding(4.dp))
        else
            Text(text = "")

        if(isCreateAccount)   UserNameInput(userNameState = username,enabled=!loading)

    EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions{
        passwordFocusRequest.requestFocus()
    })

    PasswordInput(
        modifier=Modifier.focusRequester(passwordFocusRequest),
        passwordState=password,
        labelId="Password",
        enabled=!loading,
        passwordVisibility=passwordVisibility,
        onAction=KeyboardActions{
            if(!valid) return@KeyboardActions
            onDone(username.value.trim(),email.value.trim(),password.value.trim())
        }
    )

        SubmitButton(
            textId=if(isCreateAccount) "Create Account" else "Login",
            loading=loading,
            validInputs=valid
        ){
            onDone(username.value.trim(),email.value.trim(),password.value.trim())
        }
    }
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                onClick:()->Unit) {
Button(
    onClick = onClick,
    modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(0.7f)
        ,
    enabled = !loading && validInputs,
    shape = CircleShape,
    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),


    ) {
    if(loading)
        CircularProgressIndicator(modifier = Modifier.size(25.dp), color = Color.Green)
    else
        Text(text = textId, modifier = Modifier.padding(5.dp))
}
}


@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  labelId: String,
                  enabled: Boolean,
                  passwordVisibility: MutableState<Boolean>,
                  imeAction: ImeAction=ImeAction.Done,
                  onAction: KeyboardActions=KeyboardActions.Default) {
    val visualTransformation=if(passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction)


}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible=passwordVisibility.value
    IconButton(onClick = {passwordVisibility.value=!visible}) {
        Icons.Default.Close
    }
}


@Composable
fun EmailInput(
    modifier:Modifier=Modifier,
    emailState:MutableState<String>,
    labelId:String="Email",
    enabled:Boolean=true,
    imeAction: ImeAction=ImeAction.Next,
    onAction:KeyboardActions= KeyboardActions.Default
){
    
    InputField(modifier = modifier,
        valueState = emailState,
        labelId ="Email" ,
        enabled = enabled,
    keyboardType = KeyboardType.Email,
    imeAction = imeAction,
    onAction = onAction)

}


@Composable
fun InputField(
    modifier:Modifier=Modifier,
    valueState:MutableState<String>,
        labelId: String,
    isSingleLine:Boolean=true,
    enabled: Boolean,
    keyboardType:KeyboardType= KeyboardType.Text,
    imeAction: ImeAction= ImeAction.Next,
    onAction: KeyboardActions=KeyboardActions.Default
){
    OutlinedTextField(value = valueState.value,
        onValueChange = {valueState.value=it},
        label= {Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color= MaterialTheme.colors.onBackground),
        modifier= modifier
            .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction),
        keyboardActions = onAction

    )

}


@Composable
fun UserNameInput( modifier:Modifier=Modifier,
                   userNameState:MutableState<String>,
                   labelId:String="Username",
                   enabled:Boolean=true,
                   isSingleLine:Boolean=true,
                   keyboardType:KeyboardType= KeyboardType.Text,
                   imeAction: ImeAction= ImeAction.Next,
                   onAction: KeyboardActions=KeyboardActions.Default

){
    OutlinedTextField(value = userNameState.value,
        onValueChange = {userNameState.value=it},
        label= {Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color= MaterialTheme.colors.onBackground),
        modifier= modifier
            .padding(start = 10.dp, bottom = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled=enabled,
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType, imeAction = imeAction),
        keyboardActions = onAction

    )

}
