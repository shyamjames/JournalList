package com.example.journallist.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.journallist.model.Mood
import com.example.journallist.ui.theme.DesertOnPrimary
import com.example.journallist.ui.theme.DesertOnSurface
import com.example.journallist.ui.theme.DesertOnSurfaceVariant
import com.example.journallist.ui.theme.DesertOutlineVariant
import com.example.journallist.ui.theme.DesertPrimary
import com.example.journallist.ui.theme.DesertSurfaceContainer
import com.example.journallist.ui.theme.DesertSurfaceContainerLow
import com.example.journallist.ui.theme.DesertSurfaceContainerLowest
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ripple

@Composable
fun BentoCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = DesertSurfaceContainerLow,
    cornerRadius: Dp = 24.dp,
    elevation: Dp = 2.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val shape = remember(cornerRadius) { RoundedCornerShape(cornerRadius) }

    val cardModifier = modifier
        .then(
            if (elevation > 0.dp) {
                Modifier.shadow(elevation = elevation, shape = shape)
            } else Modifier
        )
        .clip(shape)
        .background(backgroundColor)
        .border(
            width = 1.dp,
            color = DesertOutlineVariant.copy(alpha = 0.5f),
            shape = shape
        )

    if (onClick != null) {
        Box(
            modifier = cardModifier
                .clickable(onClick = onClick)
        ) {
            content()
        }
    } else {
        Box(modifier = cardModifier) {
            content()
        }
    }
}

@Composable
fun FilterChip(
    label: String,
    emoji: String? = null,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val bgColor = if (isSelected) {
        DesertPrimary
    } else {
        DesertSurfaceContainerLowest
    }

    val textColor = if (isSelected) DesertOnPrimary else DesertOnSurface

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(CircleShape)
            .background(bgColor)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) DesertPrimary else DesertOutlineVariant,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        if (emoji != null) {
            Text(text = emoji, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(6.dp))
        } else {
            // Keep height consistent with emoji chips
            Spacer(modifier = Modifier.height(20.dp))
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = textColor
        )
    }
}

@Composable
fun MoodChip(
    mood: Mood,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    FilterChip(
        label = mood.label,
        emoji = mood.emoji,
        isSelected = isSelected,
        onClick = onClick
    )
}

@Composable
fun TagChip(
    text: String,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(if (isSelected) DesertPrimary.copy(alpha = 0.15f) else DesertSurfaceContainer)
            .border(
                width = 1.dp,
                color = if (isSelected) DesertPrimary else DesertOutlineVariant.copy(alpha = 0.5f),
                shape = CircleShape
            )
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = if (text.startsWith("#")) text else "#$text",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp
            ),
            color = if (isSelected) DesertPrimary else DesertOnSurfaceVariant
        )
    }
}

@Composable
fun PillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isPrimary: Boolean = true,
    enabled: Boolean = true
) {
    val bgColor = when {
        !enabled -> DesertOutlineVariant
        isPrimary -> DesertPrimary
        else -> Color.Transparent
    }

    val textColor = when {
        !enabled -> DesertOnSurfaceVariant
        isPrimary -> DesertOnPrimary
        else -> DesertPrimary
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .background(bgColor)
            .border(
                width = if (!isPrimary) 2.dp else 0.dp,
                color = if (!isPrimary) DesertPrimary else Color.Transparent,
                shape = CircleShape
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(horizontal = 24.dp, vertical = 14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = textColor
            )
        }
    }
}

@Composable
fun DesertTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DesertSurfaceContainerLow)
            .border(
                width = 1.5.dp,
                color = DesertOutlineVariant.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = textStyle.copy(color = DesertOnSurfaceVariant.copy(alpha = 0.6f))
            )
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            textStyle = textStyle.copy(color = DesertOnSurface),
            cursorBrush = SolidColor(DesertPrimary),
            keyboardOptions = keyboardOptions,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
