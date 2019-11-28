package online.ahayujie.aha_vocabulary_app.data.bean;

/**
 * 接口response status json
 */
public class StatusJson {

    private int status; //0为失败，1为成功

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusJson{" +
                "status=" + status +
                '}';
    }
}
