# Vocabumate

## Summary

Vocabulary acquisition is a crucial aspect of language learning, but it can often be daunting and time-consuming. Many learners struggle to find effective ways to memorize new words and understand their usage in context. Enter Vocabumate, the ultimate solution for language enthusiasts looking to expand their lexicon effortlessly. By automating the process of fetching new words, their meanings, and contextual examples it significantly reduces the time and effort traditionally associated with vocabulary expansion.

## Solution

Language learners today face a range of issues such as limited exposure to new vocabulary, difficulties in retaining and applying newly learned words, grasping their contextual usage, and a general lack of curation, accessibility, and convenience in existing learning methods. Moreover, integrating vocabulary learning into daily life and maintaining consistency in learning habits pose additional hurdles.

Vocabumate provides learners with a comprehensive platform by leveraging the vast database powered by Google Gemini AI. It offers extensive examples and contextual sentences, ensuring learners not only memorize words but also grasp their nuances and connotations. This approach fosters a deeper interpretation and retention, addressing the aforementioned challenges effectively.

Vocabumate distinguishes itself through several innovative features. For starters, its automated word discovery personalizes the learning experience by sourcing new words daily, tailored to the user's preferences and learning goals. This ensures a steady stream of fresh vocabulary, crucial for continuous learning and retention.

Interactive flashcards facilitate active recall and reinforce word memorization through an engaging and user-friendly interface. These flashcards are complemented by a Spaced Repetition System (SRS), which optimizes learning retention by intelligently scheduling review sessions based on the user's performance, ensuring that vocabulary is revisited at optimal intervals for long-term memory solidification.

Moreover, Vocabumate offers offline access and cross-platform syncing, making learning accessible anytime, anywhere, and on any device. This flexibility, combined with features such as multilingual support, personalized daily goals, reminders, and progress tracking, empowers learners to stay motivated, track their growth, and achieve their language goals efficiently.

Vocabumate's intuitive design, customization options, and emphasis on social learning further enhance the learning experience. It stands as a testament to the power of innovative technology in overcoming traditional barriers to language learning, paving the way for learners to effortlessly integrate vocabulary learning into their daily lives.

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
  - Backend - SpringBoot

- Expected constraints:
  - Google Gemini - 60 QPM (queries per minute)
