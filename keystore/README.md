# Fixed debug signing key

Debug builds from GitHub Actions are signed with this committed PKCS12 keystore
(password: android), so every build has the SAME signature and can be installed
over the previous one without uninstalling.

- Alias: androiddebugkey
- Password: android
- Store type: PKCS12
