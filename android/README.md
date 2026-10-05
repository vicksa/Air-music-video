# Air Music para Android

Aplicativo Android instalável, com a interface e os instrumentos incluídos no APK e exibidos em um WebView Android. A câmera recebe permissão pelo Android; o MediaPipe processa as mãos no aparelho. A biblioteca e o modelo de visão são carregados pela internet. Os quadros da câmera não são enviados a um servidor pelo aplicativo.

## Compilar

Requisitos: Java 17 ou 21, Android SDK 35 e Build Tools 35.0.0.

```sh
cd android
./gradlew assembleDebug
```

APK: `app/build/outputs/apk/debug/app-debug.apk`.

Esta primeira versão é assinada com a chave de desenvolvimento. Para distribuir na Play Store, configure uma chave de produção e gere um AAB assinado.

## Instalar

Baixe o APK em Releases, abra no celular e permita a instalação pelo navegador/gerenciador de arquivos quando o Android pedir. Abra **Air Music**, toque em **Ativar câmera** e conceda acesso à câmera. Android 8 ou superior; mantenha o Android System WebView atualizado.

## Verificação no aparelho

Confira permissão concedida/negada, ativação e parada da câmera, piano, guitarra com duas mãos, desafio, sensibilidade e retorno após colocar o aplicativo em segundo plano.
