package br.com.attornatus.api.exceptionhandler;

import br.com.attornatus.domain.exception.EnderecoPrincipalJaExistenteException;
import br.com.attornatus.domain.exception.EnderecoPrincipalNaoEncontradoException;
import br.com.attornatus.domain.exception.PessoaNaoEncontradaException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MSG_ERRO_GENERICA_USUARIO_FINAL =
      "Ocorreu um erro interno inesperado no sistema. Tente novamente, e se o "
          + "problema persistir, entre em contato com o administrador do sistema.";

  private final MessageSource messageSource;

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
    String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

    log.error(ex.getMessage(), ex);
    ex.printStackTrace();
    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();
    return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
  }

  @ExceptionHandler(EnderecoPrincipalNaoEncontradoException.class)
  public ResponseEntity<Object> handleEnderecoPrincipalNaoEncontradoException(
      EnderecoPrincipalNaoEncontradoException ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    ProblemType problemType = ProblemType.ENDERECO_NAO_ENCONTRADO;
    String detail = ex.getMessage();

    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
  }

  @ExceptionHandler(EnderecoPrincipalJaExistenteException.class)
  public ResponseEntity<Object> handleEnderecoPrincipalJaExistenteException(
      EnderecoPrincipalJaExistenteException ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
    ProblemType problemType = ProblemType.PRINCIPAL_JA_CADASTRADO;
    String detail = ex.getMessage();

    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
  }

  @ExceptionHandler(PessoaNaoEncontradaException.class)
  public ResponseEntity<Object> handlePessoaNaoEncontradaException(
      PessoaNaoEncontradaException ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    ProblemType problemType = ProblemType.PESSOA_NAO_ENCONTRADA;
    String detail = ex.getMessage();

    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<Object> handleDateTimeParseException(
      DateTimeParseException ex, WebRequest request) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
    String detail =
        "O formato de data enviado n??o ?? permitido, favor enviar no seguinte padr??o: dd/MM/aaaa";

    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), httpStatus, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Throwable rootCause = ExceptionUtils.getRootCause(ex);

    if (rootCause instanceof InvalidFormatException cause) {
      return handleInvalidFormatException(
              cause, headers, status, request);
    } else if (rootCause instanceof PropertyBindingException cause) {
      return handlePropertyBindingException(
              cause, headers, status, request);
    }
    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail = "O corpo da requisi????o est?? inv??lido. Verifique erro de sintaxe!";
    Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    if (ex instanceof MethodArgumentTypeMismatchException ex2) {
      return handleMethodArgumentTypeMismatchException(
              ex2, headers, status, request);
    }
    return super.handleTypeMismatch(ex, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    if (body == null) {
      body =
          Problem.builder()
              .timestamp(OffsetDateTime.now())
              .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
              .status(status.value())
              .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
              .build();
    } else if (body instanceof String body2) {
      body =
          Problem.builder()
              .timestamp(OffsetDateTime.now())
              .title(body2)
              .status(status.value())
              .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
              .build();
    }

    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
  }

  private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
    String detail =
        String.format(
            "O par??metro de URL '%s' recebeu o valor '%s', que ?? de um tipo inv??lido. "
                + "Corrija e informe um valor compat??vel com o tipo %s!",
            ex.getName(),
            ex.getValue(),
            Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
    Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handleValidationInternal(
      Exception ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request,
      BindingResult bindingResult) {
    ProblemType problemType = ProblemType.DADOS_INVALIDOS;
    String detail =
        "Um ou mais campos est??o inv??lidos. Fa??a o preenchimento correto e tente novamente.";

    List<Problem.Object> problemObjects =
        bindingResult.getAllErrors().stream()
            .map(
                objectError -> {
                  String message =
                      messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                  String name = objectError.getObjectName();

                  if (objectError instanceof FieldError objectError2) {
                    name = (objectError2).getField();
                  }

                  return Problem.Object.builder().name(name).userMessage(message).build();
                })
            .toList();

    Problem problem =
        createProblemBuilder(status, problemType, detail)
            .userMessage(detail)
            .objects(problemObjects)
            .build();

    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handlePropertyBindingException(
      PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String path = joinPath(ex.getPath());
    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail =
        String.format(
            "A propriedade '%s' n??o existe. "
                + "Corrija ou remova essa propriedade e tente novamente.",
            path);
    Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();
    return handleExceptionInternal(ex, problem, headers, status, request);
  }

  private ResponseEntity<Object> handleInvalidFormatException(
      InvalidFormatException ex,
      HttpHeaders headers,
      HttpStatusCode httpStatus,
      WebRequest request) {

    String path = joinPath(ex.getPath());

    ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
    String detail =
        String.format(
            "A propriedade '%s' recebeu o valor '%s',"
                + "que ?? de um tipo inv??lido. Corrija e informe um valor compat??vel com o tipo %s.",
            path, ex.getValue(), ex.getTargetType().getSimpleName());
    Problem problem =
        createProblemBuilder(httpStatus, problemType, detail).userMessage(detail).build();

    return handleExceptionInternal(ex, problem, headers, httpStatus, request);
  }

  private String joinPath(List<JsonMappingException.Reference> references) {
    return references.stream()
        .map(JsonMappingException.Reference::getFieldName)
        .collect(Collectors.joining("."));
  }

  private Problem.ProblemBuilder createProblemBuilder(
      HttpStatusCode status, ProblemType problemType, String detail) {
    return Problem.builder()
        .timestamp(OffsetDateTime.now())
        .status(status.value())
        .type(problemType.getUri())
        .title(problemType.getTitle())
        .detail(detail);
  }
}
