package com.miracle.natifetest.presentation.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miracle.natifetest.R
import com.miracle.natifetest.presentation.theme.DarkBlue

@Composable
fun SearchEditText(word: String, isLoading: Boolean, onValueChange: (w: String) -> Unit) {
    BasicTextField(
        value = word,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        decorationBox = {
            Card(
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                backgroundColor = DarkBlue,
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Image(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box {
                        if (word.isEmpty())
                            Text(
                                text = stringResource(id = R.string.text_input_hint),
                                color = Color.LightGray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        it()
                    }

                    Spacer(modifier = Modifier.weight(1.0f))

                    if (isLoading)
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White
                        )
                    else
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { onValueChange("") },
                            tint = Color.LightGray
                        )
                }
            }
        }
    )
}