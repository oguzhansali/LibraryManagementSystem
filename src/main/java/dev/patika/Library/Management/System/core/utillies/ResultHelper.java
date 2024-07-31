package dev.patika.Library.Management.System.core.utillies;

import dev.patika.Library.Management.System.core.result.Result;
import dev.patika.Library.Management.System.core.result.ResultData;
import dev.patika.Library.Management.System.dto.response.CursorResponse;
import org.springframework.data.domain.Page;

public class ResultHelper {
    public static <T> ResultData<T> created(T data) {
        return new ResultData<>(true, Msg.CREATED, "201", data);

    }

    //Veri doğrulama hatası olduğunda döndürülecek ResultData
    public static <T> ResultData<T> validateEroor(T data) {
        return new ResultData<>(false, Msg.VALIDATE_ERROR, "400", data);

    }

    //İşlem başarıyla tamamlandığında döndürülecek ResultData
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true, Msg.OK, "200", data);

    }

    //Genel başarılı işlem sonucu döndürülecek Result
    public static Result ok() {
        return new Result(true, Msg.OK, "200");
    }

    //Veri bulunamadığında döndürülecek hata mesajı ile Result
    public static Result notFoundError(String msg) {
        return new Result(false, msg, "404");
    }

    //Sayfalı veri döndürüldüğünde ResultData ve CursorResponse ile birlikte döndürür
    public static <T> ResultData<CursorResponse<T>> cursor(Page<T> pageData) {
        CursorResponse<T> cursor = new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPageNumber(pageData.getNumber());
        cursor.setPageSize(pageData.getSize());
        cursor.setTotalElements(pageData.getTotalElements());
        return ResultHelper.success(cursor);

    }
}
