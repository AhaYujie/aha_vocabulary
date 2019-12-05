package online.ahayujie.aha_vocabulary_app.data.bean;

public class Test {


    /**
     * word : {"wordClean":1,"wordId":15,"wordSearchTimes":1,"wordSpell":"ass","wordTime":"Sun Dec 01 08:41:08 UTC 2019","wordTranslation":"屁股","wordUserId":10}
     * status : 1
     */

    private WordBean word;
    private int status;

    public WordBean getWord() {
        return word;
    }

    public void setWord(WordBean word) {
        this.word = word;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class WordBean {
        /**
         * wordClean : 1
         * wordId : 15
         * wordSearchTimes : 1
         * wordSpell : ass
         * wordTime : Sun Dec 01 08:41:08 UTC 2019
         * wordTranslation : 屁股
         * wordUserId : 10
         */

        private int wordClean;
        private int wordId;
        private int wordSearchTimes;
        private String wordSpell;
        private String wordTime;
        private String wordTranslation;
        private int wordUserId;

        public int getWordClean() {
            return wordClean;
        }

        public void setWordClean(int wordClean) {
            this.wordClean = wordClean;
        }

        public int getWordId() {
            return wordId;
        }

        public void setWordId(int wordId) {
            this.wordId = wordId;
        }

        public int getWordSearchTimes() {
            return wordSearchTimes;
        }

        public void setWordSearchTimes(int wordSearchTimes) {
            this.wordSearchTimes = wordSearchTimes;
        }

        public String getWordSpell() {
            return wordSpell;
        }

        public void setWordSpell(String wordSpell) {
            this.wordSpell = wordSpell;
        }

        public String getWordTime() {
            return wordTime;
        }

        public void setWordTime(String wordTime) {
            this.wordTime = wordTime;
        }

        public String getWordTranslation() {
            return wordTranslation;
        }

        public void setWordTranslation(String wordTranslation) {
            this.wordTranslation = wordTranslation;
        }

        public int getWordUserId() {
            return wordUserId;
        }

        public void setWordUserId(int wordUserId) {
            this.wordUserId = wordUserId;
        }
    }
}
