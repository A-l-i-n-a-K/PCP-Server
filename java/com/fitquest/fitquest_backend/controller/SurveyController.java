package com.fitquest.fitquest_backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/survey")
public class SurveyController {

    // Обработка POST запроса для получения ответов и расчета результата
    @PostMapping("/result")
    public Result calculateResult(@RequestBody SurveyAnswers surveyAnswers) {
        // Получаем ответы из тела запроса
        Map<Integer, Integer> answers = surveyAnswers.getAnswers();

        // Рассчитываем итоговый балл
        int totalScore = answers.values().stream().mapToInt(Integer::intValue).sum();

        // Формируем рекомендации на основе балла
        String recommendation = getRecommendation(totalScore);

        // Возвращаем результат
        return new Result(totalScore, recommendation);
    }

    // Метод для формирования рекомендаций на основе балла
    private String getRecommendation(int score) {
        if (score >= 60) {
            return "Прекрасное состояние. Вы показываете высокую мотивацию...";
        } else if (score >= 45) {
            return "Хорошее состояние. У вас есть небольшие области для улучшения...";
        } else if (score >= 30) {
            return "Удовлетворительное состояние. Возможно, вам не хватает энергии...";
        } else {
            return "Плохое состояние. Вам стоит уделить внимание своему эмоциональному состоянию...";
        }
    }

    // Вспомогательный класс для получения ответов
    public static class SurveyAnswers {
        private Map<Integer, Integer> answers;  // Карта с вопросами и ответами

        public Map<Integer, Integer> getAnswers() {
            return answers;
        }

        public void setAnswers(Map<Integer, Integer> answers) {
            this.answers = answers;
        }
    }

    // Вспомогательный класс для результата
    public static class Result {
        private int totalScore;
        private String recommendation;

        public Result(int totalScore, String recommendation) {
            this.totalScore = totalScore;
            this.recommendation = recommendation;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public String getRecommendation() {
            return recommendation;
        }

        public void setRecommendation(String recommendation) {
            this.recommendation = recommendation;
        }
    }
}
