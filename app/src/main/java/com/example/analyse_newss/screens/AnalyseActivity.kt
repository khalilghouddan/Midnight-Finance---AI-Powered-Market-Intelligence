package com.example.analyse_newss.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyse_newss.data.AppDatabase
import com.example.analyse_newss.model.Analysis
import com.example.analyse_newss.model.News
import com.example.analyse_newss.ui.theme.Analyse_newssTheme
import kotlinx.coroutines.launch
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle

class AnalyseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve News object from Intent
        val news = intent.getParcelableExtra<News>("news_data")
        
        setContent {
            Analyse_newssTheme {
                if (news != null) {
                    AnalyseScreen(news = news, onBack = { finish() })
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text("Error: No news data found")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyseScreen(news: News, onBack: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = AppDatabase.getDatabase(context)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "ANALYSIS", 
                        style = MaterialTheme.typography.titleMedium,
                        letterSpacing = 2.sp,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // News Header
            Text(
                text = news.title,
                style = MaterialTheme.typography.headlineSmall, // Slightly smaller than Large for fit
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                 Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(MaterialTheme.colorScheme.secondary, shape = androidx.compose.foundation.shape.CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = news.source.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 1.sp
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            var analysisText by remember { mutableStateOf("Initializing AI Analysis protocols...") }
            var isAnalyzing by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                analysisText = com.example.analyse_newss.outil.GeminiApiImpl.analyzeNews(
                    news.title, news.description, news.source
                )
                isAnalyzing = false
            }

            Text(
                text = "AI INSIGHTS",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (isAnalyzing) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().height(2.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Analysis Content
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.padding(20.dp)) {
                    MarkdownText(
                        text = analysisText,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            // Actions
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.url))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("READ FULL STORY", style = MaterialTheme.typography.labelLarge)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val analysis = Analysis(
                            newsTitle = news.title,
                            newsSource = news.source,
                            summary = analysisText
                        )
                        db.analysisDao().insertAnalysis(analysis)
                        Toast.makeText(context, "Analysis Saved to Vault", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(
                   containerColor = MaterialTheme.colorScheme.primary,
                   contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("SAVE REPORT", style = MaterialTheme.typography.labelLarge)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun MarkdownText(text: String, style: androidx.compose.ui.text.TextStyle) {
    val annotatedString = androidx.compose.ui.text.buildAnnotatedString {
        val boldRegex = Regex("\\*\\*(.*?)\\*\\*")
        // Simple regex for headers like ## Header
        val headerRegex = Regex("(?m)^##\\s+(.+)$")
        
        // This simple approach doesn't handle overlapping/nested well, but good enough for simple Markdown
        // Let's iterate line by line to handle headers first, then bold inlines? 
        // Or just Bold for now to be safe and robust.
        
        var lastIndex = 0
        
        boldRegex.findAll(text).forEach { result ->
            val (matchedText) = result.destructured
            val startIndex = result.range.first
            val endIndex = result.range.last + 1
            
            if (startIndex > lastIndex) {
                append(text.substring(lastIndex, startIndex))
            }
            
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)) {
                append(matchedText)
            }
            
            lastIndex = endIndex
        }
        
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }
    
    // Check if the text mimics a header (starts with #)
    // Actually, let's keep it simple. The provided markdown usually just has **Bold**.
    
    Text(
        text = annotatedString, 
        style = style, 
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
    )
}


