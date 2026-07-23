---
name: Desert Solace
colors:
  surface: '#fff8f6'
  surface-dim: '#f4d3c6'
  surface-bright: '#fff8f6'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#fff1ec'
  surface-container: '#ffe9e1'
  surface-container-high: '#ffe2d7'
  surface-container-highest: '#fddbce'
  on-surface: '#29170f'
  on-surface-variant: '#554336'
  inverse-surface: '#402c23'
  inverse-on-surface: '#ffede7'
  outline: '#887364'
  outline-variant: '#dbc2b0'
  surface-tint: '#904d00'
  primary: '#8d4b00'
  on-primary: '#ffffff'
  primary-container: '#b15f00'
  on-primary-container: '#fffbff'
  inverse-primary: '#ffb77d'
  secondary: '#6e5e0d'
  on-secondary: '#ffffff'
  secondary-container: '#f6df84'
  on-secondary-container: '#726212'
  tertiary: '#3f6355'
  on-tertiary: '#ffffff'
  tertiary-container: '#587c6d'
  on-tertiary-container: '#f5fff8'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdcc3'
  primary-fixed-dim: '#ffb77d'
  on-primary-fixed: '#2f1500'
  on-primary-fixed-variant: '#6e3900'
  secondary-fixed: '#f9e287'
  secondary-fixed-dim: '#dcc66e'
  on-secondary-fixed: '#221b00'
  on-secondary-fixed-variant: '#534600'
  tertiary-fixed: '#c4ebd9'
  tertiary-fixed-dim: '#a8cfbd'
  on-tertiary-fixed: '#002116'
  on-tertiary-fixed-variant: '#2a4d40'
  background: '#fff8f6'
  on-background: '#29170f'
  surface-variant: '#fddbce'
typography:
  display-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 48px
    fontWeight: '800'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Work Sans
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Work Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-lg:
    fontFamily: Work Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.02em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
rounded:
  sm: 0.5rem
  DEFAULT: 1rem
  md: 1.5rem
  lg: 2rem
  xl: 3rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 12px
  md: 24px
  lg: 40px
  xl: 64px
  gutter: 16px
  margin-mobile: 20px
  margin-desktop: 120px
---

## Brand & Style

The design system is centered on a "Whimsical, Cozy, and Personal" aesthetic. It draws heavily from the warmth of desert landscapes to create an emotional sanctuary for users. The visual direction is a blend of **Tactile Minimalism** and **Soft-Modernism**, prioritizing organic-feeling containers and a "bento box" structural logic.

The goal is to evoke the feeling of a physical journal or a well-loved travelogue. Every interaction should feel soft and intentional, avoiding the coldness of typical digital interfaces. This is achieved through generous rounding, a warm-biased palette, and subtle depth that makes UI elements feel like physical paper or smooth stones resting on a surface.

## Colors

The color strategy uses "Earth & Oasis" tones.
- **Primary (Ochre/Terracotta):** Used for key actions, brand moments, and high-emphasis states. It provides the core warmth.
- **Secondary (Sand/Cream):** Primarily used for container backgrounds and large surfaces. It acts as a soft "page" color that reduces eye strain.
- **Tertiary (Sage Green):** Inspired by desert flora, this is used for success states, secondary highlights, or to differentiate specific categories (like moods).
- **Neutral (Dark Chocolate):** A rich, warm dark tone used for high-contrast text and iconography. Avoid pure black to maintain the organic feel.

The color mode is strictly light-themed, utilizing layered shades of sand and cream to create hierarchy rather than dark mode reversals.

## Typography

Typography balances personality with legibility. 
- **Headings:** We use **Plus Jakarta Sans** for its rounded, friendly terminals and modern geometric structure. It feels approachable yet professional. High-level headers should use "ExtraBold" or "Bold" weights to anchor the page.
- **Body & Data:** **Work Sans** is used for its exceptional clarity and neutral character. It provides a grounded counterpoint to the more expressive display type. 
- **Styling Note:** Headlines should maintain tight letter-spacing for a cohesive "sticker" look, while body text requires standard spacing to ensure readability in long-form diary entries.

## Layout & Spacing

The design system utilizes a **Bento Box** layout philosophy. Content is organized into distinct, highly-rounded modules that sit together with consistent gaps.

- **Grid:** A 12-column fluid grid for desktop and a 4-column grid for mobile.
- **The Rhythm:** We use an 8px baseline grid. Padding within cards should be generous (typically 24px or 32px) to emphasize the sense of "space" and "calm."
- **Stacking:** Vertically, components use the `md` (24px) spacing unit to ensure a breathable flow. 
- **Mobile Reflow:** On mobile, bento cards that were side-by-side on desktop should stack vertically, maintaining the same corner radius and internal padding to preserve the tactile feel.

## Elevation & Depth

This design system avoids harsh shadows or industrial depth. Instead, it uses **Ambient Soft Shadows** and **Tonal Layering**.

- **Surface Tiers:** The background is the lowest level (`#FAF7F2`). Cards sit on top of this background, often using a slightly lighter or warmer tone (`#FFF`) or a subtle secondary color.
- **Shadow Profile:** Shadows are extremely diffused with low opacity (10-15%). They use a hint of the primary color in the shadow's tint (a warm brown/terracotta tint) rather than grey, making the objects feel like they belong to the environment.
- **Interactive Depth:** On hover or tap, cards should appear to lift slightly (increasing shadow blur) or depress slightly (inset shadow) to provide tactile feedback.

## Shapes

The shape language is the primary driver of the "whimsical" mood. Sharp corners are strictly prohibited. 
- **Standard Cards:** Use `rounded-2xl` (1.5rem / 24px).
- **Feature Modules:** Use `rounded-3xl` (2rem / 32px) to create a distinct, bubbly appearance.
- **Buttons & Chips:** Always use pill-shaped (fully rounded) containers.
- **Icons:** Should follow a "soft-line" style with rounded caps and joins to match the surrounding UI containers.

## Components

### Buttons
Primary buttons are pill-shaped, filled with the Primary Ochre, and use Dark Chocolate text for a distinct high-contrast look. Secondary buttons use a thick 2px border in the Primary color with no fill.

### Bento Cards
The core unit of the design system. Cards must have a 24px to 32px corner radius. They should use a subtle 1px border in a slightly darker sand tone to define edges without adding visual weight.

### Chips
Used for tags and mood categories. These are small, pill-shaped elements using Tertiary Sage or Secondary Sand backgrounds. They use `label-lg` typography.

### Input Fields
Inputs are large with 16px vertical padding. They utilize a soft cream background instead of white to minimize contrast and maintain the cozy aesthetic. The focus state is a 2px Primary color ring.

### Progress & Tracking
Use thick, rounded stroke widths for any tracking bars or "pathway" maps (as seen in the reference), emphasizing the playful, gamified nature of the diary.