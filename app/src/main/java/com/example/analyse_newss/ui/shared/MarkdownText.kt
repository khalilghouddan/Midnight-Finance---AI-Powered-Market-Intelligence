package com.example.analyse_newss.ui.shared

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun MarkdownText(
    text: String, 
    style: androidx.compose.ui.text.TextStyle,
    color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Unspecified,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    val annotatedString = buildAnnotatedString {
        val boldRegex = Regex("\\*\\*(.*?)\\*\\*")
        var lastIndex = 0
        
        boldRegex.findAll(text).forEach { result ->
            val (matchedText) = result.destructured
            val startIndex = result.range.first
            val endIndex = result.range.last + 1
            
            if (startIndex > lastIndex) {
                append(text.substring(lastIndex, startIndex))
            }
            
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(matchedText)
            }
            
            lastIndex = endIndex
        }
        
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }
    
    Text(text = annotatedString, style = style, color = color, modifier = modifier)
}
