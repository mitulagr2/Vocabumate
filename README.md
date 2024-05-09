# Vocabumate

## Summary

Memorize new words, understand their usage, and expand your lexicon effortlessly.

## Solution

Vocabumate automates the process of fetching new words, their meanings, and contextual examples, significantly reducing the time and effort traditionally associated with vocabulary expansion.

### Features

1. **Automated Word Discovery:** Automatically sources and presents new words daily, tailored to the user's preferences and learning goals.

2. **Contextual Meanings and Examples:** Utilizes Google Gemini AI on entering a new word to provide detailed information including definition, pronunciation, and usage in varied contexts.

3. **Interactive Flashcards:** Facilitates active recall and memorization through swiping flashcards, viewing meanings, and examples, and marking familiarity.

4. **Spaced Repetition System (SRS):** Schedules review sessions based on user performance to optimize retention and ensure long-term memory solidification.

5. **Offline Access and Cross-Platform Syncing:** Allows studying anytime, anywhere, without an internet connection and supports seamless progress syncing across devices.

6. **Multilingual Support:** Caters to a global user base by supporting multiple languages, allowing learning in one's native language or any language of choice.

7. **Personalized Daily Goals and Reminders:** Enables users to set daily goals and receive reminders, encouraging consistent learning habits and goal achievement.

8. **Progress Tracking:** Features an intuitive dashboard for monitoring vocabulary growth, accuracy rates, and study time, motivating users towards their language goals.

9. **User-Friendly Interface:** Offers a clean, intuitive design with customization options for themes, font sizes, etc., enhancing usability for users of all ages and technical backgrounds.

10. **Social Learning:** Facilitates learning through social interaction and collaboration within the app's community.

## Designs

[Figma Link](https://www.figma.com/file/naRB583z9oNflnVXseW9qN/Vocabumate?type=design&node-id=0%3A1&mode=design&t=ryVDLteKr67FSuQG-1)

## Phases

### Phase 0 / V0 / Alpha

Single screen app with an input that uses Google Gemini AI and displays the generated text.

### Phase 1 / V1 / Beta (Optional)

Words marked for memorization are accessible offline and home screen shows flash cards for revision.

### Phase 2 / V2 / Release Candidate (Optional)

Suggest new words to learn and keep track of daily streak.

### Phase 3 / V3

Implement cross-platform syncing and a basic streak leaderboard.

### Phase 4 / V4

Improves progress tracking and add weekly content to discover page.

### Phase N / Future Scope

Multilingual support and personalized goal configuration.

## Implementation / Technical Details

- Tech stack:

  - Frontend - Kotlin with Jetpack Compose
  - Backend - Node.js

- Expected constraints:
  - Google Gemini - 60 QPM (queries per minute)
