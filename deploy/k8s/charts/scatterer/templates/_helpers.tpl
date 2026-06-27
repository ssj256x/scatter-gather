{{/* Common Labels for all Resources */}}
{{- define "common.labels" -}}
app: {{ .Release.Name }}
app.kubernetes.io/name: {{ .Release.Name }}
app.kubernetes.io/instance: {{ .Release.Name }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/part-of: {{ .Values.partOf }}
{{- end -}}

{{/* Image Name Generator */}}
{{- define "image.value" -}}
{{ .Values.container.image.name }}:{{ .Values.container.image.tag }}
{{- end -}}

