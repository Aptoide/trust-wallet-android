package com.asfoundation.wallet.ui.gamification

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.asf.wallet.R

class GamificationMapper(private val context: Context) {

  fun mapCurrentLevelInfo(level: Int): CurrentLevelInfo {
    return when (level) {
      0 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_earth),
          getColor(R.color.gamification_light_green),
          getString(R.string.gamif_card_start),
          getString(R.string.gamif_quote_earth))
      1 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_moon),
          getColor(R.color.gamification_light_grey),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_moon),
          getString(R.string.gamif_quote_moon))
      2 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_mars),
          getColor(R.color.gamification_red),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_mars),
          getString(R.string.gamif_quote_mars))
      3 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_phobos),
          getColor(R.color.gamification_blue_grey),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_phobos),
          getString(R.string.gamif_quote_phobos))
      4 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_jupiter),
          getColor(R.color.gamification_orange),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_jupiter),
          getString(R.string.gamif_quote_jupiter))
      5 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_europa),
          getColor(R.color.gamification_dark_yellow),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_europa),
          getString(R.string.gamif_quote_europa))
      6 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_saturn),
          getColor(R.color.gamification_yellow),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_saturn),
          getString(R.string.gamif_quote_saturn))
      7 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_titan),
          getColor(R.color.gamification_blue_green),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_titan),
          getString(R.string.gamif_quote_titan))
      8 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_uranus),
          getColor(R.color.gamification_old_blue),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_uranus),
          getString(R.string.gamif_quote_uranus))
      9 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_neptune),
          getColor(R.color.gamification_blue),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_neptune),
          getString(R.string.gamif_quote_neptune))
      10 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_purple),
          getColor(R.color.gamification_purple),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
      11 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_green),
          getColor(R.color.gamification_green),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
      12 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_brown),
          getColor(R.color.gamification_brown),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
      13 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_blue),
          getColor(R.color.gamification_light_blue),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
      14 -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_red),
          getColor(R.color.gamification_dark_red),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
      else -> CurrentLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_purple),
          getColor(R.color.gamification_purple),
          getFullString(R.string.gamif_card_title, R.string.gamif_placeholder_unknown),
          getString(R.string.gamif_quote_planetx))
    }
  }

  fun mapReachedLevelInfo(level: Int): ReachedLevelInfo {
    return when (level) {
      0 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_earth_reached),
          getString(R.string.gamif_achievement_start),
          getString(R.string.gamif_achievement_start_sub))
      1 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_moon_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_moon),
          getString(R.string.gamif_distance_moon))
      2 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_mars_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_mars),
          getString(R.string.gamif_distance_mars))
      3 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_phobos_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_phobos),
          getString(R.string.gamif_distance_phobos))
      4 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_jupiter_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_jupiter),
          getString(R.string.gamif_distance_jupiter))
      5 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_europa_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_europa),
          getString(R.string.gamif_distance_europa))
      6 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_saturn_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_saturn),
          getString(R.string.gamif_distance_saturn))
      7 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_titan_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_titan),
          getString(R.string.gamif_distance_titan))
      8 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_uranus_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_uranus),
          getString(R.string.gamif_distance_uranus))
      9 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_neptune_reached),
          getFullString(R.string.gamif_achievement_reach, R.string.gamif_placeholder_neptune),
          getString(R.string.gamif_distance_neptune))
      10 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_purple_reached),
          getUnknownPlanetString(10),
          getString(R.string.gamif_distance_unkown))
      11 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_green_reached),
          getUnknownPlanetString(11),
          getString(R.string.gamif_distance_unkown))
      12 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_brown_reached),
          getUnknownPlanetString(12),
          getString(R.string.gamif_distance_unkown))
      13 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_blue_reached),
          getUnknownPlanetString(13),
          getString(R.string.gamif_distance_unkown))
      14 -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_red_reached),
          getUnknownPlanetString(14),
          getString(R.string.gamif_distance_unkown))
      else -> ReachedLevelInfo(getDrawable(R.drawable.gamification_unknown_planet_purple_reached),
          getUnknownPlanetString(15),
          getString(R.string.gamif_distance_unkown))
    }
  }

  fun mapLevelUpPercentage(level: Int): Int {
    //TODO these values need to be defined
    return when (level) {
      0, 1, 2, 3 -> 80
      4 -> 90
      5 -> 93
      6 -> 94
      7 -> 98
      8, 9 -> 95
      else -> 98
    }
  }

  fun getOvalBackground(levelColor: Int): Drawable? {
    val ovalBackground =
        ResourcesCompat.getDrawable(context.resources, R.drawable.oval_grey_background, null)
    ovalBackground?.let { drawable ->
      DrawableCompat.setTint(drawable.mutate(), levelColor)
    }
    return ovalBackground
  }

  fun getRectangleGamificationBackground(levelColor: Int): Drawable? {
    val rectangleGamificationLevel =
        ResourcesCompat.getDrawable(context.resources, R.drawable.rectangle_gamification_level,
            null)
    rectangleGamificationLevel?.let { drawable ->
      DrawableCompat.setTint(drawable.mutate(), levelColor)
    }
    return rectangleGamificationLevel
  }

  private fun getDrawable(@DrawableRes drawable: Int) =
      ResourcesCompat.getDrawable(context.resources, drawable, null)

  private fun getColor(@ColorRes color: Int) =
      ResourcesCompat.getColor(context.resources, color, null)

  private fun getString(@StringRes string: Int) = context.getString(string)

  private fun getFullString(@StringRes string: Int, @StringRes planet: Int) =
      context.getString(string, getString(planet))

  private fun getUnknownPlanetString(number: Int) =
      context.getString(R.string.gamif_achievement_reach,
          context.getString(R.string.gamif_placeholder_planetx, number.toString()))
}