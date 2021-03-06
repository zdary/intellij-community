// Copyright 2000-2021 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.jetbrains.python

import com.intellij.openapi.util.IconLoader
import training.ui.welcomeScreen.OnboardingLessonPromoter
import training.util.switchOnExperimentalLessons
import javax.swing.Icon
import javax.swing.JPanel

class PyOnboardingTourPromoter : OnboardingLessonPromoter("python.onboarding") {
  override fun promoImage(): Icon = IconLoader.getIcon("img/pycharm-onboarding-tour.png", PyOnboardingTourPromoter::class.java)

  override fun getPromotionForInitialState(): JPanel? {
    if (!switchOnExperimentalLessons) return null
    return super.getPromotionForInitialState()
  }
}