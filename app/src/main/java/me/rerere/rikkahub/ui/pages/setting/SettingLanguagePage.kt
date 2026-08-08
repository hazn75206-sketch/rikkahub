package me.rerere.rikkahub.ui.pages.setting

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.rerere.rikkahub.R
import me.rerere.rikkahub.ui.components.nav.BackButton
import me.rerere.rikkahub.ui.components.ui.CardGroup
import me.rerere.rikkahub.ui.theme.CustomColors
import me.rerere.rikkahub.utils.plus
import org.koin.androidx.compose.koinViewModel

private const val LANGUAGE_PREF = "language_pref"
private const val LANGUAGE_KEY = "language"

private fun languageOptions(): List<Pair<String, Int>> = listOf(
    "system" to R.string.language_system,
    "en" to R.string.language_english,
    "id" to R.string.language_indonesian,
    "zh" to R.string.language_simplified_chinese,
    "zh-rTW" to R.string.language_traditional_chinese,
    "ja" to R.string.language_japanese,
    "ko-rKR" to R.string.language_korean,
    "ru" to R.string.language_russian,
)

@Composable
fun SettingLanguagePage(vm: SettingVM = koinViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val settings by vm.settings.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentLanguage = settings.language

    Scaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                title = {
                    Text(stringResource(R.string.setting_page_language))
                },
                navigationIcon = {
                    BackButton()
                },
                scrollBehavior = scrollBehavior,
                colors = CustomColors.topBarColors
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = CustomColors.topBarColors.containerColor
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding + PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                CardGroup(
                    modifier = Modifier.padding(horizontal = 8.dp),
                ) {
                    languageOptions().forEach { (code, labelRes) ->
                        item(
                            onClick = {
                                if (code == currentLanguage) return@item
                                vm.updateSettings(settings.copy(language = code))
                                context.getSharedPreferences(LANGUAGE_PREF, Context.MODE_PRIVATE)
                                    .edit()
                                    .putString(LANGUAGE_KEY, code)
                                    .apply()
                                (context as? Activity)?.recreate()
                            },
                            trailingContent = {
                                RadioButton(
                                    selected = code == currentLanguage,
                                    onClick = null
                                )
                            },
                            headlineContent = { Text(stringResource(labelRes)) },
                        )
                    }
                }
            }
        }
    }
}
