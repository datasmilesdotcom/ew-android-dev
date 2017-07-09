package app.mobile.examwarrior.util.validator;

import java.util.List;

public interface ValidatorCallbacks
{
	void onSuccess();

	void onFailed(List<FormError> errors);
}
